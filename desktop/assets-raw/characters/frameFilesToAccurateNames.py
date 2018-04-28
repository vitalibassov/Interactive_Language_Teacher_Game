import os


def change_names(path, name):
    counter = 1
    for filename in os.listdir(path):
        current_name = os.path.join(path, filename)
        new_name = os.path.join(path,"{}_{:02d}.png".format(name, counter))
        os.rename(current_name, new_name)
        counter += 1


change_names(".\\player\\playerFront", "playerFront")

change_names(".\\player\\playerLeft", "playerLeft")

change_names(".\\player\\playerRight", "playerRight")

change_names(".\\player\\playerDown", "playerDown")

change_names(".\\player\\playerUp", "playerUp")