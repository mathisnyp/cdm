# Step 1: Load and Prepare the Dataset

import fitz  # PyMuPDF
import pandas as pd
from sklearn.model_selection import train_test_split

# Load the labels
labels_df = pd.read_csv('reports/report_labels.csv')

# Function to extract text from a PDF file
def extract_text_from_pdf(file_path):
    doc = fitz.open(file_path)
    text = ""
    for page in doc:
        text += page.get_text()
    return text

# Read the report text and store it in the DataFrame
labels_df['text'] = labels_df['Report_ID'].apply(lambda x: extract_text_from_pdf(f'reports/report_{x}.pdf'))

# Split the data into training and validation sets
train_texts, val_texts, train_labels, val_labels = train_test_split(labels_df['text'], labels_df['Label'].map({'Real': 0, 'Fake': 1}), test_size=.2)

# Step 2: Preprocess the Text Data for BERT

from transformers import BertTokenizer

tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')

train_encodings = tokenizer(train_texts.tolist(), truncation=True, padding=True, max_length=512)
val_encodings = tokenizer(val_texts.tolist(), truncation=True, padding=True, max_length=512)

# Step 3: Convert Data to PyTorch Datasets

import torch

class ReportsDataset(torch.utils.data.Dataset):
    def __init__(self, encodings, labels):
        self.encodings = encodings
        self.labels = labels

    def __getitem__(self, idx):
        item = {key: torch.tensor(val[idx]) for key, val in self.encodings.items()}
        item['labels'] = torch.tensor(self.labels[idx])
        return item

    def __len__(self):
        return len(self.labels)

train_dataset = ReportsDataset(train_encodings, train_labels.tolist())
val_dataset = ReportsDataset(val_encodings, val_labels.tolist())

# Step 4: Load and Fine-Tune the BERT Model

from transformers import BertForSequenceClassification, Trainer, TrainingArguments

model = BertForSequenceClassification.from_pretrained('bert-base-uncased', num_labels=2)  # 2 labels: Real or Fake

training_args = TrainingArguments(
    output_dir='./results',          # output directory for model and logs
    num_train_epochs=3,              # number of training epochs
    per_device_train_batch_size=8,   # batch size per device during training
    per_device_eval_batch_size=16,   # batch size for evaluation
    warmup_steps=500,                # number of warmup steps for learning rate scheduler
    weight_decay=0.01,               # strength of weight decay
    logging_dir='./logs',            # directory for storing logs
    logging_steps=10,
)

trainer = Trainer(
    model=model,
    args=training_args,
    train_dataset=train_dataset,
    eval_dataset=val_dataset
)

trainer.train()

# Step 5: Evaluate the Model
trainer.evaluate()

# Save the fine-tuned model and the tokenizer
model_path = 'models/model-1'
model.save_pretrained(model_path)
tokenizer.save_pretrained(model_path)
