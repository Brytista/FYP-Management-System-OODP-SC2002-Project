import os
import pandas as pd

from generators.students import generate_student_df
from generators.supervisors import generate_supervisor_df
from generators.fypcoordinators import generate_fypcoord_df
from generators.projects import generate_project_df

if __name__ == "__main__":

    PATH = "../SC2002Project/src/files"
    print("Generating files...")

    generate_student_df(PATH)
    supervisor_df = generate_supervisor_df(PATH)
    generate_fypcoord_df(PATH)
    generate_project_df(PATH, supervisor_df)

    print("Done!")