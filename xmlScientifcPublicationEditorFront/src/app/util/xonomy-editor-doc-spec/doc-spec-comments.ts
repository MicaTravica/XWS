declare const Xonomy: any;
export const docSpec = {
    elements: {
        'ns:scientificPublication': {
            menu: [
                {
                    caption: 'Append an <ns:comments>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:comments xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:comment id="comment" ref=""><ns:content id="content"/></ns:comment></ns:comments>',
                    hideIf: function (jsElement) {
                        return jsElement.hasChildElement('ns:comments');
                    }
                }
            ]
        },
        'ns:comments': {
            mustBeAfter: ['ns:caption', 'ns:authors', 'ns:abstract', 'ns:chapter', 'ns:references'],
            canDropTo: ['ns:scientificPublication'],
            menu: [
                {
                    caption: 'Append an <ns:comment>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:comment id="comment" ref="" xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:content id="content"/></ns:comment>'
                },
                {
                    caption: 'Delete this comments',
                    action: Xonomy.deleteElement
                }
            
            ]
        },
        'ns:text': {
            canDropTo: ['ns:paragraph', 'ns:content'],
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
            canDropTo: ['ns:paragraph', 'ns:content'],
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
            canDropTo: ['ns:paragraph', 'ns:content'],
        },
        'ns:list': {
            canDropTo: ['ns:paragraph', 'ns:content'],
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
        'ns:comment': {
            canDropTo: ['ns:comments'],
            menu: [
                {
                    caption: 'Delete this comment',
                    action: Xonomy.deleteElement,
                    hideIf: function (jsElement) {
                        return jsElement.parent().getChildElements('ns:comment').length < 2;
                    }
                }],
            attributes: {
                'ref': {
                    asker: Xonomy.askString,
                }
            }
        },
        'ns:content': {
            canDropTo: ['ns:comment'],
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
                }
            ]
        }
    }
};
