import os

path = ".\\shepherd"
name = "shepherd"
counter = 1

for filename in os.listdir(path):
    current_name = os.path.join(path, filename)
    new_name = os.path.join(path,"{}_{:02d}.png".format(name, counter))
    os.rename(current_name, new_name)
    counter += 1
