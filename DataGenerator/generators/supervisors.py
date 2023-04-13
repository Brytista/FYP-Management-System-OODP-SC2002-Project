import os
import pandas as pd


def generate_supervisor_df(PATH):

    data_path = os.path.join(PATH, "initial_input", "faculty_list.xlsx")
    data = pd.read_excel(data_path)

    supervisor_dict = {
        "userID": [],
        "password": [],
        "name": [],
        "email": [],
        "numOfProjects": [],
        "studentManaged": [],
    }

    for i in range(len(data)):

        row = data.iloc[i]

        name = row["Name"]
        if ',' in name:
            name = name.replace(',', '')

        email = row["Email"]
        userID = email.split("@")[0]

        supervisor_dict["userID"].append(userID)
        supervisor_dict["password"].append("password")
        supervisor_dict["name"].append(name)
        supervisor_dict["email"].append(email)
        supervisor_dict["numOfProjects"].append(0)
        supervisor_dict["studentManaged"].append("None;")

    supervisor_df = pd.DataFrame(supervisor_dict)
    supervisor_df.to_csv(os.path.join(
        PATH, "supervisorList.csv"), index=False, header=False)

    return supervisor_df
