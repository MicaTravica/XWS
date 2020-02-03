declare const Xonomy: any;
export const docSpec = {
    elements: {
        'ns:scientificPublication': {
            menu: [
                {
                    caption: 'Append an <ns:comments>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:comments xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:comment id="comment" ref=""></ns:comment></ns:comments>',
                    hideIf: function (jsElement) {
                        return jsElement.hasChildElement('ns:comments');
                    }
                }
            ]
        },
        'ns:comments': {
            mustBeAfter: ['ns:caption', 'ns:authors', 'ns:abstract', 'ns:chapter', 'ns:references', 'ns:dateMetaData'],
            canDropTo: ['ns:scientificPublication'],
            menu: [
                {
                    caption: 'Append an <ns:comment>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:comment id="comment" ref="" xmlns:ns="http://www.uns.ac.rs/Tim1"></ns:comment>'
                },
                {
                    caption: 'Delete this comments',
                    action: Xonomy.deleteElement
                }
            ]
        },
        'ns:comment': {
            canDropTo: ['ns:comments'],
            onelinear: true,
            menu: [
                {
                    caption: 'Delete this comment',
                    action: Xonomy.deleteElement,
                    hideIf: function (jsElement) {
                        return jsElement.parent().getChildElements('ns:comment').length < 2;
                    }
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
            ],
            attributes: {
                'ref': {
                    asker: Xonomy.askString,
                }
            }
        }
    }
};
