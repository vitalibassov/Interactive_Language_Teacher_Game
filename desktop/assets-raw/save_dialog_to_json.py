import json


def write_to_JSON_file(path, filename, data):
    with open(path + '/' + filename + '.json', 'w') as outfile:
        json.dump(data, outfile)


conversation = {
    0: {
        "merchant": {
            0: {
                "text": "[MERCHANT] Some first text Lorem Ipsum is simply dummy text of the printing and typesetting "
                        "industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
                        "when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
                        "It has survived not only five centuries, but also the leap into electronic typesetting, "
                        "remaining essentially unchanged. It was popularised in the 1960s with the release of "
                        "Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing "
                        "software like Aldus PageMaker including versions of Lorem Ipsum.",
                "answers": [
                    {
                        "destinationID": 1,
                        "score": 25,
                        "text": "[MERCHANT] Answer 1"
                    },
                    {
                        "destinationID": 2,
                        "score": 50,
                        "text": "[MERCHANT] Answer 2"
                    }
                ]
            },
            1: {
                "text": "[MERCHANT] Some Text With Index 1 It is a long established fact that a reader will be "
                        "distracted by the readable content of a page when looking at its layout. The point of using "
                        "Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to "
                        "using 'Content here, content here', making it look like readable English. Many desktop "
                        "publishing packages and web page editors now use Lorem Ipsum as their default model text, "
                        "and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various "
                        "versions have evolved over the years, sometimes by accident, sometimes on purpose (injected "
                        "humour and the like).",
                "answers": [
                    {
                        "destinationID": 1,
                        "score": 75,
                        "text": "[MERCHANT] Answer 1 in Index 1"
                    },
                    {
                        "destinationID": 2,
                        "score": 150,
                        "text": "[MERCHANT] Answer 2 in Index 1 Contrary to popular belief, Lorem Ipsum is not simply "
                                "random text. It has roots in a piece of classical Latin literature from 45 BC, "
                                "making it over 2000 years old. "
                    },
                    {
                        "destinationID": 3,
                        "score": 200,
                        "text": "[MERCHANT] Answer 3 in Index 1"
                    }
                ]
            },
            2: {
                "text": "[MERCHANT] Some Text With Index 2",
                "answers": [
                    {
                        "destinationID": 1,
                        "score": 20,
                        "text": "[MERCHANT] Answer 1 in Index 2"
                    },
                    {
                        "destinationID": 2,
                        "score": 5,
                        "text": "[MERCHANT] Answer 2 in Index 2"
                    },
                    {
                        "destinationID": 3,
                        "score": 15,
                        "text": "[MERCHANT] Answer 3 in Index 2"
                    }
                ]
            },
            3: {
                "text": "[MERCHANT] Some Text With Index 3 Should Be EXIT",
                "answers": [
                    {
                        "destinationID": -1,
                        "score": 1000,
                        "text": "[MERCHANT] Okay GoodBye"
                    }
                ]
            }
        }
    },
    1: {
        "player": {
            0: {
                "text": "AUTHOR SPEECH HERE BLASDKJHASDKJAHSDKJASHKLD Some Text With Index 1 It is a long established "
                        "fact that a reader will be "
                        "distracted by the readable content of a page when looking at its layout. The point of using "
                        "Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to "
                        "using 'Content here, content here', making it look like readable English. Many desktop "
                        "publishing packages and web page editors now use Lorem Ipsum as their default model text, "
                        "and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various "
                        "versions have evolved over the years, sometimes by accident, sometimes on purpose (injected "
                        "humour and the like)AS fkJHDFKJDS dgk sdgkj hs dsjfgsdkjf dlfksdlkf sdfjsdlkf sdfkj lsdkjf "
                        "lsdkjf lsdkjf lsdkjf lskdj lkdfgjofdkjhg okfds jklsdhf jlk Ipsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed toIpsum is that it has a "
                        "more-or-less normal distribution of letters, as opposed to ",
                "answers": []
            }
        }
    },
    2: {
        "shepherd": {
            0: {
                "text": "[SHEPHERD] Some first text",
                "answers": [
                    {
                        "destinationID": 1,
                        "score": 100,
                        "text": "[SHEPHERD] Answer 1"
                    },
                    {
                        "destinationID": 2,
                        "score": 50,
                        "text": "[SHEPHERD] Answer 2"
                    }
                ]
            },
            1: {
                "text": "[SHEPHERD] Some Text With Index 1 (Test new words: disturb, util, declaration, development)",
                "answers": [
                    {
                        "destinationID": 1,
                        "score": 15,
                        "text": "[SHEPHERD] Answer 1 in Index 1"
                    },
                    {
                        "destinationID": 2,
                        "score": 20,
                        "text": "[SHEPHERD] Answer 2 in Index 1"
                    },
                    {
                        "destinationID": 3,
                        "score": 20,
                        "text": "[SHEPHERD] Answer 3 in Index 1"
                    }
                ]
            },
            2: {
                "text": "[SHEPHERD] Some Text With Index 2",
                "answers": [
                    {
                        "destinationID": 1,
                        "score": 700,
                        "text": "[SHEPHERD] Answer 1 in Index 2"
                    },
                    {
                        "destinationID": 2,
                        "score": 5,
                        "text": "[SHEPHERD] Answer 2 in Index 2"
                    },
                    {
                        "destinationID": 3,
                        "score": 5,
                        "text": "[SHEPHERD] Answer 3 in Index 2"
                    }
                ]
            },
            3: {
                "text": "[SHEPHERD] Some Text With Index 3 Should Be EXIT",
                "answers": [
                    {
                        "destinationID": -1,
                        "score": 5000,
                        "text": "[SHEPHERD] Okay GoodBye"
                    }
                ]
            }
        }
    },
    3: {
        "merchant": {
            0: {
                "text": "[SHEPHERD] Some first text",
                "answers": [
                    {
                        "destinationID": 1,
                        "score": 100,
                        "text": "[SHEPHERD] Answer 1"
                    },
                    {
                        "destinationID": 2,
                        "score": 100,
                        "text": "[SHEPHERD] Answer 2"
                    }
                ]
            },
            1: {
                "text": "[SHEPHERD] Some Text With Index 1",
                "answers": [
                    {
                        "destinationID": 1,
                        "score": 100,
                        "text": "[SHEPHERD] Answer 1 in Index 1"
                    },
                    {
                        "destinationID": 2,
                        "score": 100,
                        "text": "[SHEPHERD] Answer 2 in Index 1"
                    },
                    {
                        "destinationID": 3,
                        "score": 100,
                        "text": "[SHEPHERD] Answer 3 in Index 1"
                    }
                ]
            },
            2: {
                "text": "[SHEPHERD] Some Text With Index 2",
                "answers": [
                    {
                        "destinationID": 1,
                        "score": 222,
                        "text": "[SHEPHERD] Answer 1 in Index 2"
                    },
                    {
                        "destinationID": 2,
                        "score": 15,
                        "text": "[SHEPHERD] Answer 2 in Index 2"
                    },
                    {
                        "destinationID": 3,
                        "score": 7,
                        "text": "[SHEPHERD] Answer 3 in Index 2"
                    }
                ]
            },
            3: {
                "text": "[SHEPHERD] Some Text With Index 3 Should Be EXIT",
                "answers": [
                    {
                        "destinationID": -1,
                        "score": 10000,
                        "text": "[SHEPHERD] Okay GoodBye"
                    }
                ]
            }
        }
    }
}

write_to_JSON_file("./", "level_1", conversation)
