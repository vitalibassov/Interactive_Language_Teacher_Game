final_string = ""

with open("dictionary_raw.txt", 'r', encoding="utf8") as file:
    for line in file:
        line = line.replace("!NEW!", "").replace("(TR!)", "").replace("\t", "").replace(" -- ", "\t")
        final_string += line


new_file = open("dictionary.txt", 'w', encoding="utf8")
new_file.write(final_string)
new_file.close()

