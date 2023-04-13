import os
import pandas as pd


def generate_student_df(PATH):

    data_path = os.path.join(PATH, "initial_input", "student_list.xlsx")
    data = pd.read_excel(data_path)

    student_dict = {
        "userID": [],
        "password": [],
        "name": [],
        "email": [],
        "projectID": [],
        "requestProject": [],
        "isDeregistered": [],
    }

    for i in range(len(data)):

        row = data.iloc[i]

        name = row["Name"]
        email = row["Email"]
        if ';' in email:
            email = email.replace(';', '')

        userID = email.split("@")[0]

        student_dict["userID"].append(userID)
        student_dict["password"].append("password")
        student_dict["name"].append(name)
        student_dict["email"].append(email)
        student_dict["projectID"].append(-1)
        student_dict["requestProject"].append("false")
        student_dict["isDeregistered"].append("false")

    student_df = pd.DataFrame(student_dict)
    student_df.to_csv(os.path.join(PATH, "studentList.csv"),
                      index=False, header=False)
