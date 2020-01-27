declare const Xonomy: any;

export const docSpec = {
    elements: {
        'ns:questionnaire': {
            menu: [],
            attributes: {
            }
        },
        'ns:questions' : {
            menu: [
                {
                    canDropTo: ['ns:questions'],
                    caption: 'Append an <ns:question>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:question id="q1" xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                }
            ]
        },
        'ns:question': {
            menu: [
                {
                    caption: 'Append an <ns:questionText>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:questionText id="t1" xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                },
                {
                    caption: 'Append an <ns:answer>',
                    action: Xonomy.newElementChild,
                    actionParameter: '<ns:answer id="a1" xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
                }
            ]
        },
        'ns:mark': {
            menu: [
                {
                    caption: 'Set mark'
                }
            ]
        },
        'ns:name': {
            oneliner: true,
            mustBeBefore: ['ns:surname', 'ns:email', 'ns:phone', 'ns:institution', 'ns:address'],
            canDropTo: ['ns:reviwer', 'ns:institution'],
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
                }
            }
            ]
        },
        'ns:surname': {
            oneliner: true,
            mustBeAfter: ['ns:name'],
            mustBeBefore: ['ns:email', 'ns:phone', 'ns:institution'],
            canDropTo: ['ns:reviwer'],
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
                }
            }
            ]
        },
        'ns:email': {
            oneliner: true,
            mustBeAfter: ['ns:name', 'ns:surname'],
            mustBeBefore: ['ns:phone', 'ns:institution'],
            canDropTo: ['ns:reviwer'], menu: [
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
                }
            }
            ]
        },
        'ns:phone': {
            oneliner: true,
            mustBeAfter: ['ns:name', 'ns:surname', 'ns:email'],
            mustBeBefore: ['ns:institution'],
            canDropTo: ['ns:reviwer'],
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
                }
            }
            ]
        },
        'ns:institution': {
            mustBeAfter: ['ns:name', 'ns:surname', 'ns:email', 'ns:phone'],
            canDropTo: ['ns:reviwer'],
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
                return jsElement.hasChildElement('streetNumber');
                }
            }, {
                caption: 'Append an <ns:floorNumber>',
                action: Xonomy.newElementChild,
                actionParameter: '<ns:floorNumber xmlns:ns="http://www.uns.ac.rs/Tim1"> </ns:floorNumber>',
                hideIf: function (jsElement) {
                return jsElement.hasChildElement('floorNumber');
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
                }
            }
            ]
        },
    }
};
