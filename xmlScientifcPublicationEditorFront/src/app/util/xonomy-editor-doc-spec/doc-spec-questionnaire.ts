declare const Xonomy: any;

export const docSpec = {
  elements: {
    'ns:questionnaire': {
      menu: [
        {
          caption: 'Append an <ns:content>',
          action: Xonomy.newElementChild,
          actionParameter: '<ns:content id="c1" xmlns:ns="http://www.uns.ac.rs/Tim1"/>'
        }
      ],
      attributes: {
      }
    },
    'ns:reviewer': {
      mustBeAfter: ['ns:date'],
      mustBeBefore: ['ns:questions'],
      menu: [
        {
          caption: 'Add my data',
          action: myData,
          actionParameter: localStorage.getItem('user') ? localStorage.getItem('user').split('ns:person').join('ns:reviewer') : ''
        }
      ]
    },
    'ns:date': {
      menu: [{
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
      }]
    },
    'ns:questions': {
      canDropTo: ['ns:questions'],
      menu: [
        {
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
        },
        {
          caption: 'Delete this question',
          action: Xonomy.deleteElement,
          hideIf: function (jsElement) {
            return jsElement.parent().getChildElements('ns:question').length < 2;
          }
        }
      ]
    },
    'ns:questionText': {
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
    'ns:mark': {
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
          },
          hideIf: function (jsElement) {
            return jsElement.getText() !== '';
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
    // nadole je paragraf i ono sto se nalazi u njemu...
    // za answer i content

    'ns:answer': {
      menu: [
        {
          caption: 'Append an <ns:text>',
          action: Xonomy.newElementChild,
          actionParameter: '<ns:text id="text" xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:cursive/></ns:text>'
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
          caption: 'Delete this paragraph',
          action: Xonomy.deleteElement,
          hideIf: function (jsElement) {
            return jsElement.parent().getChildElements('ns:answer').length < 2;
          }
        }
      ]
    },
    'ns:text': {
      canDropTo: ['ns:answer', 'ns:content'],
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
    'ns:formula': {
      canDropTo: ['ns:answer', 'ns:content'], menu: [
        {
          caption: 'Delete this list',
          action: Xonomy.deleteElement
        }
      ],
    },
    'ns:list': {
      canDropTo: ['ns:answer', 'ns:content'],
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
      mustBeAfter: ['ns:date', 'ns:reviewer'],
      mustBeBefore: ['ns:questions', 'ns:mark'],
      menu: [
        {
          caption: 'Append an <ns:text>',
          action: Xonomy.newElementChild,
          actionParameter: '<ns:text id="text" xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:cursive/></ns:text>'
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
