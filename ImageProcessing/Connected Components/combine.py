with open("combined.txt", "a") as output:
    label = "labelfile"
    prop = "propertyFile"
    rf = "rfprettyprint"
    data = [1, 2]
    connected = [4,8]
    for x in connected:
        for y in data:
            output.write("Full output for " + rf+str(y)+str(x)+".txt:\n")
            with open(rf+str(y)+str(x)+".txt", "r") as file:
                for line in file:
                    output.write(line)
            output.write("\nLabel Print-out for " + rf+str(y)+str(x)+".txt:\n")
            with open(label+str(y)+str(x)+".txt", "r") as file:
                for line in file:
                    output.write(line)
            output.write("\nProperty File Print-out for " + rf+str(y)+str(x)+".txt:\n")
            with open(prop+str(y)+str(x)+".txt", "r") as file:
                for line in file:
                    output.write(line)
            output.write("\n")
    