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
          caption: 'Append an <ns:image>',
          action: Xonomy.newElementChild,
          actionParameter: '<ns:image id="image" xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:description/><ns:source/></ns:image>'
        }, {
          caption: 'Append an <ns:table>',
          action: Xonomy.newElementChild,
          actionParameter: '<ns:table id="table" border="true" xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:table_row><ns:table_cell/></ns:table_row></ns:table>'
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
    'ns:quote': {
      canDropTo: ['ns:answer', 'ns:content'],
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
      canDropTo: ['ns:answer', 'ns:content'],
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
    'ns:image': {
      canDropTo: ['ns:answer'],
      menu: [
        {
          caption: 'Delete this image',
          action: Xonomy.deleteElement,
        },
        {
          caption: 'Add @height="100"',
          action: Xonomy.newAttribute,
          actionParameter: { name: 'nheight', value: '100' },
          hideIf: function (jsElement) {
            return jsElement.hasAttribute('height');
          }
        }, {
          caption: 'Add @width="100"',
          action: Xonomy.newAttribute,
          actionParameter: { name: 'width', value: '100' },
          hideIf: function (jsElement) {
            return jsElement.hasAttribute('width');
          }
        },
      ],
      attributes: {
        'height': {
          asker: Xonomy.askString,
        },
        'width': {
          asker: Xonomy.askString,
        }
      }
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
          caption: 'Append an <ns:image>',
          action: Xonomy.newElementChild,
          actionParameter: '<ns:image id="image" xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:description/><ns:source/></ns:image>'
        }, {
          caption: 'Append an <ns:table>',
          action: Xonomy.newElementChild,
          actionParameter: '<ns:table id="table" border="true" xmlns:ns="http://www.uns.ac.rs/Tim1"><ns:table_row><ns:table_cell/></ns:table_row></ns:table>'
        },
        {
          caption: 'Delete this content',
          action: Xonomy.deleteElement,
        }
      ]
    }
  }
};
