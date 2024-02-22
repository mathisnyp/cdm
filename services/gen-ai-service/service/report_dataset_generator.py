from faker import Faker
import random
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
import os
import pandas as pd

# Create a reports folder if it doesn't exist
reports_folder = 'reports'
if not os.path.exists(reports_folder):
    os.makedirs(reports_folder)

fake = Faker()

def generate_report_content(report_id):
    emergency_types = ["Fire", "Flood", "Earthquake", "Power Outage"]
    severity_levels = ["Low", "Medium", "High", "Critical"]

    # Your existing setup for fake data generation
    labels = ["Real", "Fake"]  # Possible labels

    # Build the report dictionary
    report = {
        "id": report_id,
        "name": fake.sentence(nb_words=3),
        "location": fake.address(),
        "emergencyType": random.choice(emergency_types),
        "emergencySeverity": random.choice(severity_levels),
        "generalDescription": "\n".join(fake.paragraphs(nb=random.randint(2, 5))),
        "timeReceived": fake.date_time_this_year(before_now=True, after_now=False).strftime("%Y-%m-%dT%H:%M:%S"),
        "label": random.choice(labels)  # Ensure this line is executed for each report
    }
    return report

def create_pdf(report, file_name):
    c = canvas.Canvas(file_name, pagesize=letter)
    width, height = letter
    margin = 72  # 1 inch margin

    y_position = height - margin
    line_height = 14

    for key, value in report.items():
        c.drawString(margin, y_position, f"{key}: {value}")
        y_position -= line_height

        # Check if we need a new page
        if y_position < margin:
            c.showPage()
            y_position = height - margin

    c.save()

labels_dict = {}  # Initialize a dictionary to hold report_id: label pairs

for i in range(10000):
    report_content = generate_report_content(i + 1)
    labels_dict[report_content["id"]] = report_content["label"]  # Store the label in the dictionary
    file_name = os.path.join(reports_folder, f"report_{i + 1}.pdf")
    create_pdf(report_content, file_name)  # Create a PDF file with the content

# Convert the labels dictionary to a DataFrame
labels_df = pd.DataFrame(list(labels_dict.items()), columns=['Report_ID', 'Label'])

# Save the DataFrame to a CSV file
labels_df.to_csv(os.path.join(reports_folder, 'report_labels.csv'), index=False)
