from faker import Faker
import random
import os
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas


fake = Faker()

# Expanded list of emergency types
emergency_types = [
    "Fire", "Flood", "Earthquake", "Power Outage", "Chemical Spill",
    "Traffic Accident", "Medical Emergency", "Structural Collapse"
]

# Severity levels
severity_levels = ["Low", "Medium", "High", "Critical"]

# Function to generate report content
def generate_report_content(report_id, label):
    # Select emergency type and severity based on label
    emergency_type = random.choice(emergency_types)
    severity = random.choice(severity_levels)

    # Generate consistent or inconsistent descriptions based on the label
    if label == "True":
        description = f"A {emergency_type.lower()} occurred, resulting in a {severity.lower()} impact."
    else:
        # Generate descriptions that might not match the emergency type or severity for false reports
        inconsistent_type = random.choice([et for et in emergency_types if et != emergency_type])
        description = f"Reported as a {inconsistent_type.lower()}, but it was actually a {emergency_type.lower()}. Severity was misreported as {random.choice(severity_levels).lower()}."

    report = {
        "id": report_id,
        "name": fake.sentence(nb_words=6),
        "location": fake.address(),
        "emergencyType": emergency_type,
        "emergencySeverity": severity,
        "generalDescription": description,
        "timeReceived": fake.date_time_this_year(before_now=True, after_now=False).strftime("%Y-%m-%dT%H:%M:%S"),
        "label": label
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

        if y_position < margin:
            c.showPage()
            y_position = height - margin

    c.save()


# Create a reports folder if it doesn't exist
reports_folder = 'reports'
if not os.path.exists(reports_folder):
    os.makedirs(reports_folder)

# Generate and save reports
for i in range(10000):
    label = random.choice(["True", "False"])
    report_content = generate_report_content(i + 1, label)
    file_name = os.path.join(reports_folder, f"report_{i + 1}.pdf")
    create_pdf(report_content, file_name)

