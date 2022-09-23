mylist = []
for x in range(1,4):
    for y in range(1,3):
        mylist.append("output" + str(y) + str(x) + ".txt")

with open("outputs.txt", "a") as file:
    for _ in mylist:
        with open(_, "r") as file2:
            for line in file2:
                file.write(line)
        file.write("\n")