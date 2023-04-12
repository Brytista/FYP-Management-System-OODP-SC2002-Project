import os
import pandas as pd


def generate_project_df(PATH, supervisor_df):

    data_path = os.path.join(PATH, "initial_input", "projects.xlsx")
    data = pd.read_excel(data_path)

    project_dict = {
        "projectID": [],
        "supervisorID": [],
        "projectTitle": [],
        "projectStatus": [],
        "studentID": [],
    }

    for i in range(len(data)):

        row = data.iloc[i]

        supervisor_name = row["Supervisor"]
        if ',' in supervisor_name:
            supervisor_name = supervisor_name.replace(',', '')

        title = row["Title"]
        supervisorID = search_supervisorID(supervisor_name, supervisor_df)

        project_dict["projectID"].append(-1)
        project_dict["supervisorID"].append(supervisorID)
        project_dict["projectTitle"].append(title)
        project_dict["projectStatus"].append("AVAILABLE")
        project_dict["studentID"].append("None")

    project_df = pd.DataFrame(project_dict)
    project_df.to_csv(os.path.join(PATH, "projectList.csv"),
                      index=False, header=False)

def search_supervisorID(name, supervisor_df):

    return supervisor_df[supervisor_df["name"] == name]["userID"].values[0]