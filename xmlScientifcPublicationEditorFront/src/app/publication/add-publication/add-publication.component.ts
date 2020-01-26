import { Component, OnInit } from '@angular/core';
import { PublicationService } from 'src/app/services/publication-service/publication.service';
declare const Xonomy: any;

@Component({
  selector: 'app-add-publication',
  templateUrl: './add-publication.component.html',
  styleUrls: ['./add-publication.component.scss']
})
export class AddPublicationComponent implements OnInit {

  publicationXml = '';

  constructor(private publicationService: PublicationService) { }

  ngOnInit() {
    const docSpec = {
      onchange: function () {
        console.log('I been changed now!')
      },
      validate: function (obj) {
        console.log('I be validatin\' now!')
      },
      elements: {
        'scientificPublication': {
          menu: [
            {
              caption: 'Append an <chapter>',
              action: Xonomy.newElementChild,
              actionParameter: '<chapter id="chapter" title="some title"><paragraph id="paragraph"/></chapter>'
            },
            {
              caption: 'Append an <references>',
              action: Xonomy.newElementChild,
              actionParameter: '<references><reference id="reference"/></references>',
              hideIf: function (jsElement) {
                return jsElement.hasChildElement('references');
              }
            },
            {
              caption: 'Append an <comments>',
              action: Xonomy.newElementChild,
              actionParameter: '<comments><comment id="comment" ref=""><content id="content"/></comment></comments>',
              hideIf: function (jsElement) {
                return jsElement.hasChildElement('comments');
              }
            }],
          attributes: {
            'tableOfContent': {
              asker: Xonomy.askPicklist,
              askerParameter: [
                'true', 'false'
              ]
            }
          }
        },
        'caption': {
          mustBeBefore: ['authors', 'abstract', 'chapter', 'references', 'comments'],
          canDropTo: ['scientificPublication'],
        },
        'value': {
          oneliner: true,
          canDropTo: ['caption'],
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
        'authors': {
          mustBeAfter: ['caption'],
          mustBeBefore: ['abstract', 'chapter', 'references', 'comments'],
          canDropTo: ['scientificPublication'],
          menu: [
            {
              caption: 'Append an <author>',
              action: Xonomy.newElementChild,
              actionParameter: '<author id="author"><name/><surname/><email/><phone/><institution id="institution"><name/><address><city/><street/><country/></address></institution></author>'
            }]
        },
        'abstract': {
          mustBeAfter: ['caption', 'authors'],
          mustBeBefore: ['chapter', 'references', 'comments'],
          canDropTo: ['scientificPublication'],
          menu: [{
            caption: 'Append an <paragraph>',
            action: Xonomy.newElementChild,
            actionParameter: '<paragraph id="paragraph"/>'
          },
          ]
        },
        'chapter': {
          mustBeAfter: ['caption', 'authors', 'abstract'],
          mustBeBefore: ['references', 'comments'],
          canDropTo: ['scientificPublication'],
          menu: [{
            caption: 'Append an <paragraph>',
            action: Xonomy.newElementChild,
            actionParameter: '<paragraph id="paragraph"/>'
          }, {
            caption: 'Append an <subchapter>',
            action: Xonomy.newElementChild,
            actionParameter: '<subchapter id="subchapter" title="some title"><paragraph id="paragraph"/></subchapter>'
          }],
          attributes: {
            'title': {
              asker: Xonomy.askString,
            }
          }
        },
        'references': {
          mustBeAfter: ['caption', 'authors', 'abstract', 'chapter'],
          mustBeBefore: ['comments'],
          canDropTo: ['scientificPublication'],
          menu: [
            {
              caption: 'Append an <reference>',
              action: Xonomy.newElementChild,
              actionParameter: '<reference id="reference"/>'
            },
          ]
        },
        'comments': {
          mustBeAfter: ['caption', 'authors', 'abstract', 'chapter', 'references'],
          canDropTo: ['scientificPublication'],
          menu: [{
            caption: 'Append an <comment>',
            action: Xonomy.newElementChild,
            actionParameter: '<comment id="comment" ref=""><content id="content"/></comment>'
          },]
        },
        'author': {
          canDropTo: ['authors'],
          menu: [
            {
              caption: 'Delete this author',
              action: Xonomy.deleteElement,
              hideIf: function (jsElement) {
                return jsElement.parent().getChildElements('author').length < 2;
              }
            }]
        },
        'name': {
          oneliner: true,
          mustBeBefore: ['surname', 'email', 'phone', 'institution', 'address'],
          canDropTo: ['author', 'institution'],
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
        'surname': {
          oneliner: true,
          mustBeAfter: ['name'],
          mustBeBefore: ['email', 'phone', 'institution'],
          canDropTo: ['author'],
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
        'email': {
          oneliner: true,
          mustBeAfter: ['name', 'surname'],
          mustBeBefore: ['phone', 'institution'],
          canDropTo: ['author'], menu: [
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
        'phone': {
          oneliner: true,
          mustBeAfter: ['name', 'surname', 'email'],
          mustBeBefore: ['institution'],
          canDropTo: ['author'],
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
        'institution': {
          mustBeAfter: ['name', 'surname', 'email', 'phone'],
          canDropTo: ['author'],
        },
        'address': {
          mustBeAfter: ['name'],
          canDropTo: ['institution'],
          menu: [
            {
              caption: 'Append an <streetNumber>',
              action: Xonomy.newElementChild,
              actionParameter: '<streetNumber> </streetNumber>',
              hideIf: function (jsElement) {
                return jsElement.hasChildElement('streetNumber');
              }
            }, {
              caption: 'Append an <floorNumber>',
              action: Xonomy.newElementChild,
              actionParameter: '<floorNumber> </floorNumber>',
              hideIf: function (jsElement) {
                return jsElement.hasChildElement('floorNumber');
              }
            }
          ]
        },
        'city': {
          oneliner: true,
          mustBeBefore: ['streetNumber', 'floorNumber', 'street', 'country'],
          canDropTo: ['address'],
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
        'streetNumber': {
          oneliner: true,
          mustBeAfter: ['city'],
          mustBeBefore: ['floorNumber', 'street', 'country'],
          canDropTo: ['address'],
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
        'floorNumber': {
          oneliner: true,
          mustBeAfter: ['city', 'streetNumber'],
          mustBeBefore: ['street', 'country'],
          canDropTo: ['address'],
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
        'street': {
          oneliner: true,
          mustBeAfter: ['city', 'streetNumber', 'floorNumber'],
          mustBeBefore: ['country'],
          canDropTo: ['address'], menu: [
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
        'country': {
          oneliner: true,
          mustBeAfter: ['city', 'streetNumber', 'floorNumber', 'street'],
          canDropTo: ['address'], menu: [
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
        'keywords': {
          mustBeBefore: ['paragraph'],
          canDropTo: ['abstract'],
          menu: [
            {
              caption: 'Append an <keyword>',
              action: Xonomy.newElementChild,
              actionParameter: '<keyword/>'
            }
          ]
        },
        'keyword': {
          canDropTo: ['keywords'],
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
            }
          },
          {
            caption: 'Delete this keyword',
            action: Xonomy.deleteElement,
            hideIf: function (jsElement) {
              return jsElement.parent().getChildElements('keyword').length < 2;
            }
          }]
        },
        'subchapter': {
          mustBeAfter: ['paragraph'],
          canDropTo: ['chapter', 'subchapter'],
          menu: [
            {
              caption: 'Append an <paragraph>',
              action: Xonomy.newElementChild,
              actionParameter: '<paragraph id="paragraph"/>'
            }, {
              caption: 'Append an <subchapter>',
              action: Xonomy.newElementChild,
              actionParameter: '<subchapter id="subchapter" title="some title"><paragraph id="paragraph"/></subchapter>'
            }
          ],
          attributes: {
            'title': {
              asker: Xonomy.askString,
            }
          }
        },
        'paragraph': {
          mustBeAfter: ['keywords'],
          mustBeBefore: ['subchapter'],
          canDropTo: ['abstract'],
          menu: [
            {
              caption: 'Append an <text>',
              action: Xonomy.newElementChild,
              actionParameter: '<text id="text"><cursive/></text>'
            },
            {
              caption: 'Append an <quote>',
              action: Xonomy.newElementChild,
              actionParameter: '<quote id="quote" ref=""><cursive/></quote>'
            },
            {
              caption: 'Append an <formula>',
              action: Xonomy.newElementChild,
              actionParameter: '<formula/>'
            },
            {
              caption: 'Append an <list>',
              action: Xonomy.newElementChild,
              actionParameter: '<list id="list" type="ordered"><listitem><cursive/></listitem></list>'
            },
            {
              caption: 'Append an <image>',
              action: Xonomy.newElementChild,
              actionParameter: '<image id="image"><description/><source/></image>'
            }, {
              caption: 'Append an <table>',
              action: Xonomy.newElementChild,
              actionParameter: '<table id="table" border="true"><table_row><table_cell/></table_row></table>'
            },
            {
              caption: 'Delete this paragraph',
              action: Xonomy.deleteElement,
              hideIf: function (jsElement) {
                return jsElement.parent().getChildElements('paragraph').length < 2;
              }
            }
          ]
        },
        'text': {
          canDropTo: ['paragraph', 'content'],
          menu: [
            {
              caption: 'Delete this text',
              action: Xonomy.deleteElement
            }
          ]
        },
        'cursive': {
          menu: [
            {
              caption: 'Append an <bold>',
              action: Xonomy.newElementChild,
              actionParameter: '<bold/>'
            },
            {
              caption: 'Append an <italic>',
              action: Xonomy.newElementChild,
              actionParameter: '<italic/>'
            },
            {
              caption: 'Append an <underline>',
              action: Xonomy.newElementChild,
              actionParameter: '<underline/>'
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
        'bold': {
          canDropTo: ['cursive', 'italic', 'underline'],
          menu: [
            {
              caption: 'Append an <italic>',
              action: Xonomy.newElementChild,
              actionParameter: '<italic/>'
            },
            {
              caption: 'Append an <underline>',
              action: Xonomy.newElementChild,
              actionParameter: '<underline/>'
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
              }
            }
          ]
        },
        'italic': {
          canDropTo: ['cursive', 'bold', 'underline'],
          menu: [
            {
              caption: 'Append an <bold>',
              action: Xonomy.newElementChild,
              actionParameter: '<bold/>'
            },
            {
              caption: 'Append an <underline>',
              action: Xonomy.newElementChild,
              actionParameter: '<underline/>'
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
              }
            }
          ]

        },
        'underline': {
          canDropTo: ['cursive', 'italic', 'bold'],
          menu: [
            {
              caption: 'Append an <bold>',
              action: Xonomy.newElementChild,
              actionParameter: '<bold/>'
            },
            {
              caption: 'Append an <italic>',
              action: Xonomy.newElementChild,
              actionParameter: '<italic/>'
            },
            {
              caption: 'Delete this underline',
              action: Xonomy.deleteElement
            }, {
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
        'quote': {
          canDropTo: ['paragraph', 'content'],
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
        'formula': {
          canDropTo: ['paragraph', 'content'],
        },
        'list': {
          canDropTo: ['paragraph', 'content'],
          menu: [
            {
              caption: 'Append an <listitem>',
              action: Xonomy.newElementChild,
              actionParameter: '<listitem><cursive/></listitem>'
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
        'listitem': {
          canDropTo: ['list'],
          menu: [
            {
              caption: 'Delete this listitem',
              action: Xonomy.deleteElement,
              hideIf: function (jsElement) {
                return jsElement.parent().getChildElements('listitem').length < 2;
              }
            }
          ]
        },
        'image': {
          canDropTo: ['paragraph'],
          menu: [
            {
              caption: 'Delete this image',
              action: Xonomy.deleteElement,
            },
            {
              caption: 'Add @height="100"',
              action: Xonomy.newAttribute,
              actionParameter: { name: 'height', value: '100' },
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
        'description': {
          oneliner: true,
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
        'source': {
          oneliner: true,
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
        'table': {
          canDropTo: ['paragraph'],
          menu: [
            {
              caption: 'Append an <table_row>',
              action: Xonomy.newElementChild,
              actionParameter: '<table_row><table_cell/></table_row>'
            },
            {
              caption: 'Delete this table',
              action: Xonomy.deleteElement
            }
          ],
          attributes: {
            'border': {
              asker: Xonomy.askPicklist,
              askerParameter: [
                'true', 'false'
              ]
            }
          }
        },
        'table_row': {
          canDropTo: ['table'],
          localDropOnly: function (jsElement) {
            return jsElement.parent().getChildElements('table_row').length < 2;
          },
          menu: [
            {
              caption: 'Append an <table_cell>',
              action: Xonomy.newElementChild,
              actionParameter: '<table_cell/>'
            },
            {
              caption: 'Delete this table_row',
              action: Xonomy.deleteElement,
              hideIf: function (jsElement) {
                return jsElement.parent().getChildElements('table_row').length < 2;
              }
            }
          ]
        },
        'table_cell': {
          canDropTo: ['table_row'],
          localDropOnly: function (jsElement) {
            return jsElement.parent().getChildElements('table_cell').length < 2;
          },
          menu: [
            {
              caption: 'Append an <cursive>',
              action: Xonomy.newElementChild,
              actionParameter: '<cursive/>'
            },
            {
              caption: 'Append an <image>',
              action: Xonomy.newElementChild,
              actionParameter: '<image id="image"><description/><source/></image>'
            },
            {
              caption: 'Delete this table_cell',
              action: Xonomy.deleteElement,
              hideIf: function (jsElement) {
                return jsElement.parent().getChildElements('table_cell').length < 2;
              }
            }
          ]
        },
        'reference': {
          canDropTo: ['references'],
          menu: [
            {
              caption: 'Append an <book>',
              action: Xonomy.newElementChild,
              actionParameter: '<book/>',
              hideIf: function (jsElement) {
                return jsElement.hasElements();
              }
            },
            {
              caption: 'Append an <link>',
              action: Xonomy.newElementChild,
              actionParameter: '<link/>',
              hideIf: function (jsElement) {
                return jsElement.hasElements();
              }
            },
            {
              caption: 'Delete this reference',
              action: Xonomy.deleteElement,
              hideIf: function (jsElement) {
                return jsElement.parent().getChildElements('reference').length < 2;
              }
            }
          ]
        },
        'book': {
          oneliner: true,
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
            }, {
              caption: 'Delete this book',
              action: Xonomy.deleteElement
            }
          ]

        },
        'link': {
          oneliner: true,
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
            }, {
              caption: 'Delete this link',
              action: Xonomy.deleteElement
            }
          ]
        },
        'comment': {
          canDropTo: ['comments'],
          menu: [
            {
              caption: 'Delete this comment',
              action: Xonomy.deleteElement,
              hideIf: function (jsElement) {
                return jsElement.parent().getChildElements('comment').length < 2;
              }
            }],
          attributes: {
            'ref': {
              asker: Xonomy.askString,
            }
          }
        },
        'content': {
          canDropTo: ['comment'],
          menu: [
            {
              caption: 'Append an <text>',
              action: Xonomy.newElementChild,
              actionParameter: '<text id="text"><cursive/></text>'
            },
            {
              caption: 'Append an <quote>',
              action: Xonomy.newElementChild,
              actionParameter: '<quote id="quote" ref=""><cursive/></quote>'
            },
            {
              caption: 'Append an <formula>',
              action: Xonomy.newElementChild,
              actionParameter: '<formula/>'
            },
            {
              caption: 'Append an <list>',
              action: Xonomy.newElementChild,
              actionParameter: '<list id="list" type="ordered"><listitem><cursive/></listitem></list>'
            },
            {
              caption: 'Delete this content',
              action: Xonomy.deleteElement,
              hideIf: function (jsElement) {
                return jsElement.parent().getChildElements('content').length < 2;
              }
            }]
        }
      }
    };
    // ovo ce dolaziti sa backenda
    this.publicationXml = '<?xml version="1.0" encoding="UTF-8"?><scientificPublication xmlns="http://www.uns.ac.rs/Tim1"  id="sp1" tableOfContent="true" version="1"><caption id = "c1" ><value> Moj rad</value></caption><authors><author><name>Milica</name><surname>Travica</surname><email>mcia97@eamil.com</email><phone>123-123456</phone><institution id="ins1"><name>FTN</name><address><city>Novi Sad</city><streetNumber>1</streetNumber><floorNumber>12</floorNumber><street>Nova ulica</street><country>Serbia</country></address></institution><expertise></expertise></author><author><name>Milica</name><surname>Travica</surname><email>mcia97@eamil.com</email><phone>123-123456</phone><institution id="ins2"><name>FTN</name><address><city>Novi Sad</city><streetNumber>1</streetNumber><floorNumber>12</floorNumber><street>Nova ulica</street><country>Serbia</country></address></institution><expertise></expertise></author><author><name>Milica</name><surname>Travica</surname><email>mcia97@eamil.com</email><phone>123-123456</phone><institution id="ins3"><name>FTN</name><address><city>Novi Sad</city><streetNumber>1</streetNumber><floorNumber>12</floorNumber><street>Nova ulica</street><country>Serbia</country></address></institution><expertise></expertise></author></authors><abstract id="abs1"><keywords><keyword>rec1</keyword><keyword>druga rec</keyword><keyword>tec treca</keyword><keyword>najnovija rec</keyword><keyword>ja sam rec</keyword></keywords><paragraph id="paragraph"/></abstract></scientificPublication>';
    const editor = document.getElementById('ecitor');
    // tslint:disable-next-line: no-unused-expression
    new Xonomy.render(this.publicationXml, editor, docSpec);
  }

  addPublication() {
    this.publicationXml = Xonomy.harvest() as string;
    console.log(this.publicationXml);
    this.publicationService.addPublication(this.publicationXml)
      .subscribe(res => {
        console.log(res);
      });
  }
}
