declare const Xonomy: any;

export const docSpec = {
    elements: {
        'ns:coverLetter': {
            menu: [
                {
                    caption: 'Append an <ns:content>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:content id="content" xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                }
            ]
        },
        'ns:author': {
            mustBeAfter: ['ns:date'],
            mustBeBefore: ['ns:content'],
            menu: [
                {
                    caption: 'Add my data',
                    action: myData,
                    actionParameter: localStorage.getItem('user') ? localStorage.getItem('user').split('ns:person').join('ns:author') : ''
                }
            ]
        },
        'ns:name': {
            oneliner: true,
            mustBeBefore: ['ns:surname', 'ns:email', 'ns:phone', 'ns:institution', 'ns:address'],
            canDropTo: ['ns:author', 'ns:institution'],
            menu: [
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '';
                    }
                }
            ]
        },
        'ns:surname': {
            oneliner: true,
            mustBeAfter: ['ns:name'],
            mustBeBefore: ['ns:email', 'ns:phone', 'ns:institution'],
            canDropTo: ['ns:author'],
            menu: [
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '';
                    }
                }
            ]
        },
        'ns:email': {
            oneliner: true,
            mustBeAfter: ['ns:name', 'ns:surname'],
            mustBeBefore: ['ns:phone', 'ns:institution'],
            canDropTo: ['ns:author'], menu: [
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '';
                    }
                }
            ]
        },
        'ns:phone': {
            oneliner: true,
            mustBeAfter: ['ns:name', 'ns:surname', 'ns:email'],
            mustBeBefore: ['ns:institution'],
            canDropTo: ['ns:author'],
            menu: [
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '';
                    }
                }
            ]
        },
        'ns:institution': {
            mustBeAfter: ['ns:name', 'ns:surname', 'ns:email', 'ns:phone'],
            canDropTo: ['ns:author'],
        },
        'ns:address': {
            mustBeAfter: ['ns:name'],
            canDropTo: ['ns:institution'],
            menu: [
                {
                    caption: 'Append an <ns:streetNumber>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:streetNumber xmlns:ns="http://www.uns.ac.rs/Tim1"> </ns:streetNumber>',
                    hideIf: function (jsElement) {
                        return jsElement.hasChildElement('ns:streetNumber');
                    }
                }, {
                    caption: 'Append an <ns:floorNumber>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:floorNumber xmlns:ns="http://www.uns.ac.rs/Tim1"> </ns:floorNumber>',
                    hideIf: function (jsElement) {
                        return jsElement.hasChildElement('ns:floorNumber');
                    }
                }
            ]
        },
        'ns:city': {
            oneliner: true,
            mustBeBefore: ['ns:streetNumber', 'ns:floorNumber', 'ns:street', 'ns:country'],
            canDropTo: ['ns:address'],
            menu: [
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '';
                    }
                }
            ]
        },
        'ns:streetNumber': {
            oneliner: true,
            mustBeAfter: ['ns:city'],
            mustBeBefore: ['ns:floorNumber', 'ns:street', 'ns:country'],
            canDropTo: ['ns:address'],
            menu: [
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '';
                    }
                },
                {
                    caption: 'Delete this streetNumber',
                    action: Xonomy.deleteElement
                }
            ],
        },
        'ns:floorNumber': {
            oneliner: true,
            mustBeAfter: ['ns:city', 'ns:streetNumber'],
            mustBeBefore: ['ns:street', 'ns:country'],
            canDropTo: ['ns:address'],
            menu: [{
                caption: 'Delete this floorNumber',
                action: Xonomy.deleteElement
            },
            {
                caption: 'Edit',
                action: Xonomy.editRaw,
                actionParameter: {
                    fromJs: function (jsElement) {
                        return jsElement.getText();
                    },
                    toJs: function (txt, origElement) {
                        origElement.addText(txt);
                        return origElement;
                    }
                },
                hideIf: function (jsElement) {
                    return jsElement.getText() !== '';
                }
            }
            ]
        },
        'ns:street': {
            oneliner: true,
            mustBeAfter: ['ns:city', 'ns:streetNumber', 'ns:floorNumber'],
            mustBeBefore: ['ns:country'],
            canDropTo: ['ns:address'], menu: [
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '';
                    }
                }
            ]
        },
        'ns:country': {
            oneliner: true,
            mustBeAfter: ['ns:city', 'ns:streetNumber', 'ns:floorNumber', 'ns:street'],
            canDropTo: ['ns:address'],
            menu: [
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '';
                    }
                }
            ]
        },
        'ns:text': {
            canDropTo: ['ns:content'],
            menu: [
                {
                    caption: 'Delete this text',
                    action: Xonomy.deleteElement
                }
            ]
        },
        'ns:cursive': {
            menu: [
                {
                    caption: 'Append an <ns:bold>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:bold xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                },
                {
                    caption: 'Append an <ns:italic>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:italic xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                },
                {
                    caption: 'Append an <ns:underline>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:underline xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                },
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '' || jsElement.hasElements();
                    }
                },
                {
                    caption: 'Append text',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return '';
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return !jsElement.hasElements();
                    }
                }
            ]
        },
        'ns:bold': {
            canDropTo: ['ns:cursive', 'ns:italic', 'ns:underline'],
            menu: [
                {
                    caption: 'Append an <ns:italic>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:italic xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                },
                {
                    caption: 'Append an <ns:underline>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:underline xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                },
                {
                    caption: 'Delete this bold',
                    action: Xonomy.deleteElement
                },
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '' || jsElement.hasElements();
                    }
                },
                {
                    caption: 'Append text',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return '';
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return !jsElement.hasElements();
                    }
                }
            ]
        },
        'ns:italic': {
            canDropTo: ['ns:cursive', 'ns:bold', 'ns:underline'],
            menu: [
                {
                    caption: 'Append an <ns:bold>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:bold xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                },
                {
                    caption: 'Append an <ns:underline>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:underline xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                },
                {
                    caption: 'Delete this italic',
                    action: Xonomy.deleteElement
                },
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '' || jsElement.hasElements();
                    }
                },
                {
                    caption: 'Append text',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return '';
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return !jsElement.hasElements();
                    }
                }
            ]

        },
        'ns:underline': {
            canDropTo: ['ns:cursive', 'ns:italic', 'ns:bold'],
            menu: [
                {
                    caption: 'Append an <ns:bold>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:bold xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                },
                {
                    caption: 'Append an <ns:italic>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:italic xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                },
                {
                    caption: 'Delete this underline',
                    action: Xonomy.deleteElement
                },
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '' || jsElement.hasElements();
                    }
                },
                {
                    caption: 'Append text',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return '';
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return !jsElement.hasElements();
                    }
                }
            ]
        },
        'ns:quote': {
            canDropTo: ['ns:content'],
            menu: [
                {
                    caption: 'Delete this quote',
                    action: Xonomy.deleteElement
                }
            ],
            attributes: {
                'ref': {
                    asker: Xonomy.askString,
                }
            }
        },
        'ns:formula': {
            canDropTo: ['ns:content'],
        },
        'ns:list': {
            canDropTo: ['ns:content'],
            menu: [
                {
                    caption: 'Append an <ns:listitem>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:listitem xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:cursive/></ns:listitem>'
                },
                {
                    caption: 'Delete this list',
                    action: Xonomy.deleteElement
                }
            ],
            attributes: {
                'type': {
                    asker: Xonomy.askPicklist,
                    askerParameter: [
                        'ordered', 'unordered'
                    ]
                }
            }
        },
        'ns:listitem': {
            canDropTo: ['ns:list'],
            menu: [
                {
                    caption: 'Delete this listitem',
                    action: Xonomy.deleteElement,
                    hideIf: function (jsElement) {
                        return jsElement.parent().getChildElements('ns:listitem').length < 2;
                    }
                }
            ]
        },
        'ns:content': {
            mustBeAfter: ['ns:date', 'ns:author'],
            mustBeBefore: ['ns:contactInformation', 'ns:authorSignature'],
            canDropTo: ['ns:coverLetter'],
            menu: [
                {
                    caption: 'Append an <ns:text>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:text id="text" xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:cursive/></ns:text>'
                },
                {
                    caption: 'Append an <ns:quote>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:quote id="quote" ref="" xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:cursive/></ns:quote>'
                },
                {
                    caption: 'Append an <ns:formula>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:formula xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                },
                {
                    caption: 'Append an <ns:list>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:list id="list" type="ordered" xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:listitem><ns:cursive/></ns:listitem></ns:list>'
                },
                {
                    caption: 'Delete this content',
                    action: Xonomy.deleteElement,
                    hideIf: function (jsElement) {
                        return jsElement.parent().getChildElements('ns:content').length < 2;
                    }
                }]
        },
        'ns:date': {
            menu: [
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '';
                    }
                }
            ]
        },
        'ns:authorSignature': {
            menu: [
                {
                    caption: 'Edit',
                    action: Xonomy.editRaw,
                    actionParameter: {
                        fromJs: function (jsElement) {
                            return jsElement.getText();
                        },
                        toJs: function (txt, origElement) {
                            origElement.addText(txt);
                            return origElement;
                        }
                    },
                    hideIf: function (jsElement) {
                        return jsElement.getText() !== '';
                    }
                }
            ]
        }
    }
};
function myData(htmlID, param) {
    const jsElement = Xonomy.harvestElement(document.getElementById(htmlID)).parent();
    Xonomy.deleteElement(htmlID);
    Xonomy.newElementChild(jsElement.htmlID, param);
}
