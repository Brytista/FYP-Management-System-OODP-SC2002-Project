import os
import pandas as pd


def generate_fypcoord_df(PATH):

    data_path = os.path.join(PATH, "initial_input", "fyp_coordinator.xlsx")
    data = pd.read_excel(data_path)

    fypcoord_dict = {
        "userID": [],
        "password": [],
        "name": [],
        "email": [],
    }

    for i in range(len(data)):

        row = data.iloc[i]

        name = row["Name"]
        email = row["Email"]
        userID = email.split("@")[0]

        fypcoord_dict["userID"].append(userID)
        fypcoord_dict["password"].append("password")
        fypcoord_dict["name"].append(name)
        fypcoord_dict["email"].append(email)

    fypcoord_df = pd.DataFrame(fypcoord_dict)
    fypcoord_df.to_csv(os.path.join(
        PATH, "fypCoordinatorList.csv"), index=False, header=False)