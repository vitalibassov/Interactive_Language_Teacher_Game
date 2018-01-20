import json


def write_to_JSON_file(path, filename, data):
    with open(path + '/' + filename + '.json', 'w') as outfile:
        json.dump(data, outfile)


conversation = {
    0: {
        "merchant": {
            0: {
                "text": "[MERCHANT] Some first text",
                "answers": [
                    {
                        "destinationID": 1,
                        "text": "[MERCHANT] Answer 1"
                    },
                    {
                        "destinationID": 2,
                        "text": "[MERCHANT] Answer 2"
                    }
                ]
            },
            1: {
                "text": "[MERCHANT] Some Text With Index 1",
                "answers": [
                    {
                        "destinationID": 1,
                        "text": "[MERCHANT] Answer 1 in Index 1"
                    },
                    {
                        "destinationID": 2,
                        "text": "[MERCHANT] Answer 2 in Index 1"
                    },
                    {
                        "destinationID": 3,
                        "text": "[MERCHANT] Answer 3 in Index 1"
                    }
                ]
            },
            2: {
                "text": "[MERCHANT] Some Text With Index 2",
                "answers": [
                    {
                        "destinationID": 1,
                        "text": "[MERCHANT] Answer 1 in Index 2"
                    },
                    {
                        "destinationID": 2,
                        "text": "[MERCHANT] Answer 2 in Index 2"
                    },
                    {
                        "destinationID": 3,
                        "text": "[MERCHANT] Answer 3 in Index 2"
                    }
                ]
            },
            3: {
                "text": "[MERCHANT] Some Text With Index 3 Should Be EXIT",
                "answers": [
                    {
                        "destinationID": -1,
                        "text": "[MERCHANT] Okay GoodBye"
                    }
                ]
            }
        }
    },
    1: {
        "shepherd": {
            0: {
                "text": "[SHEPHERD] Some first text",
                "answers": [
                    {
                        "destinationID": 1,
                        "text": "[SHEPHERD] Answer 1"
                    },
                    {
                        "destinationID": 2,
                        "text": "[SHEPHERD] Answer 2"
                    }
                ]
            },
            1: {
                "text": "[SHEPHERD] Some Text With Index 1",
                "answers": [
                    {
                        "destinationID": 1,
                        "text": "[SHEPHERD] Answer 1 in Index 1"
                    },
                    {
                        "destinationID": 2,
                        "text": "[SHEPHERD] Answer 2 in Index 1"
                    },
                    {
                        "destinationID": 3,
                        "text": "[SHEPHERD] Answer 3 in Index 1"
                    }
                ]
            },
            2: {
                "text": "[SHEPHERD] Some Text With Index 2",
                "answers": [
                    {
                        "destinationID": 1,
                        "text": "[SHEPHERD] Answer 1 in Index 2"
                    },
                    {
                        "destinationID": 2,
                        "text": "[SHEPHERD] Answer 2 in Index 2"
                    },
                    {
                        "destinationID": 3,
                        "text": "[SHEPHERD] Answer 3 in Index 2"
                    }
                ]
            },
            3: {
                "text": "[SHEPHERD] Some Text With Index 3 Should Be EXIT",
                "answers": [
                    {
                        "destinationID": -1,
                        "text": "[SHEPHERD] Okay GoodBye"
                    }
                ]
            }
        }
    }
}

write_to_JSON_file("./", "level1", conversation)
