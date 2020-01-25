import { Component, OnInit } from '@angular/core';
import { PublicationService } from 'src/app/services/publication-service/publication.service';
declare const Xonomy: any;

@Component({
  selector: 'app-add-publication',
  templateUrl: './add-publication.component.html',
  styleUrls: ['./add-publication.component.scss']
})
export class AddPublicationComponent implements OnInit {

  publicationXml = '<list><item label=\'one\'/><item label=\'two\'/></list>';

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
          menu: [{
            caption: 'Add @tableOfContent="false"',
            action: Xonomy.newAttribute,
            actionParameter: { name: 'tableOfContent', value: 'false' },
            hideIf: function (jsElement) {
              return jsElement.hasAttribute('tableOfContent');
            }
          },
          {
            caption: 'Append an <chapter>',
            action: Xonomy.newElementChild,
            actionParameter: '<chapter><paragraph/></chapter>'
          },
          {
            caption: 'Append an <references>',
            action: Xonomy.newElementChild,
            actionParameter: '<references/>',
            hideIf: function (jsElement) {
              return jsElement.hasChildElement('references');
            }
          },
          {
            caption: 'Append an <comments>',
            action: Xonomy.newElementChild,
            actionParameter: '<comments/>',
            hideIf: function (jsElement) {
              return jsElement.hasChildElement('comments');
            }
          }],
          attributes: {
            'tableOfContent': {
              asker: Xonomy.askPicklist,
              askerParameter: [
                'true', 'false'
              ],
              menu: [{
                caption: 'Delete this @tableOfContent',
                action: Xonomy.deleteAttribute
              }]
            }
          }
        },
        'caption': {
          mustBeBefore: ['authors', 'abstract', 'chapter', 'references', 'comments'],
          oneliner: true,
          canDropTo: ['scientificPublication'],
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
              actionParameter: '<author><name/><surname/><email/><phone/><institution><name/><address><city/><street/><country/></address></institution></author>'
            }]
        },
        'abstract': {
          mustBeAfter: ['caption', 'authors'],
          mustBeBefore: ['chapter', 'references', 'comments'],
          canDropTo: ['scientificPublication'],
          menu: [{
            caption: 'Append an <paragraph>',
            action: Xonomy.newElementChild,
            actionParameter: '<paragraph/>'
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
            actionParameter: '<paragraph/>'
          }, {
            caption: 'Append an <subchapter>',
            action: Xonomy.newElementChild,
            actionParameter: '<subchapter><paragraph/></subchapter>'
          }]
        },
        'references': {
          mustBeAfter: ['caption', 'authors', 'abstract', 'chapter'],
          mustBeBefore: ['comments'],
          canDropTo: ['scientificPublication'],
        },
        'comments': {
          mustBeAfter: ['caption', 'authors', 'abstract', 'chapter', 'references'],
          canDropTo: ['scientificPublication'],
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
              actionParameter: '<paragraph/>'
            }, {
              caption: 'Append an <subchapter>',
              action: Xonomy.newElementChild,
              actionParameter: '<subchapter><paragraph/></subchapter>'
            },
          ]
        },
        'paragraph': {
          mustBeAfter: ['keywords'],
          mustBeBefore: ['subchapter'],
          canDropTo: ['abstract'],
          menu: [
            {
              caption: 'Append an <text>',
              action: Xonomy.newElementChild,
              actionParameter: '<text><cursive/></text>'
            },
            {
              caption: 'Append an <quote>',
              action: Xonomy.newElementChild,
              actionParameter: '<quote><cursive/></quote>'
            },
            {
              caption: 'Append an <formula>',
              action: Xonomy.newElementChild,
              actionParameter: '<formula/>'
            },
            {
              caption: 'Append an <list>',
              action: Xonomy.newElementChild,
              actionParameter: '<list><listitem/></list>'
            },
            {
              caption: 'Append an <image>',
              action: Xonomy.newElementChild,
              actionParameter: '<image><description/><soruce/></image>'
            }, {
              caption: 'Append an <table>',
              action: Xonomy.newElementChild,
              actionParameter: '<table><table_row><table_cell/></table_row></table>'
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
          canDropTo: ['paragraph'],
          menu: [
            {
              caption: 'Delete this text',
              action: Xonomy.deleteElement
            }
          ]
        },
        'cursive': {
          canDropTo: ['paragraph'],
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
            },{
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
          
        },
        'formula': {

        },
        'list': {
          
        },
        'listitem': {
          
        },
        'image': {

        },
        'description': {
          
        },
        'source': {

        },
        'table': {

        },
        'table_row': {
          
        },
        'table_cell': {
          
        },
        item: {
          menu: [{
            caption: 'Add @label="something"',
            action: Xonomy.newAttribute,
            actionParameter: { name: 'label', value: 'something' },
            hideIf: function (jsElement) {
              return jsElement.hasAttribute('label');
            }
          }, {
            caption: 'Delete this <item>',
            action: Xonomy.deleteElement
          }, {
            caption: 'New <item> before this',
            action: Xonomy.newElementBefore,
            actionParameter: '<item/>'
          }, {
            caption: 'New <item> after this',
            action: Xonomy.newElementAfter,
            actionParameter: '<item/>'
          }],
          canDropTo: ['list'],
          attributes: {
            label: {
              asker: Xonomy.askString,
              menu: [{
                caption: 'Delete this @label',
                action: Xonomy.deleteAttribute
              }]
            }
          }
        }
      }
    };
    // ovo ce dolaziti sa backenda
    const xml = '<scientificPublication id="sp1" version="1"><caption id = "c1" > Moj rad</caption><authors><author><name>Milica</name><surname>Travica</surname><email>mcia97@eamil.com</email><phone>123-123456</phone><institution id="ins1"><name>FTN</name><address><city>Novi Sad</city><streetNumber>1</streetNumber><floorNumber>12</floorNumber><street>Nova ulica</street><country>Serbia</country></address></institution><expertise></expertise></author><author><name>Milica</name><surname>Travica</surname><email>mcia97@eamil.com</email><phone>123-123456</phone><institution id="ins2"><name>FTN</name><address><city>Novi Sad</city><streetNumber>1</streetNumber><floorNumber>12</floorNumber><street>Nova ulica</street><country>Serbia</country></address></institution><expertise></expertise></author><author><name>Milica</name><surname>Travica</surname><email>mcia97@eamil.com</email><phone>123-123456</phone><institution id="ins3"><name>FTN</name><address><city>Novi Sad</city><streetNumber>1</streetNumber><floorNumber>12</floorNumber><street>Nova ulica</street><country>Serbia</country></address></institution><expertise></expertise></author></authors><abstract id="abs1"><keywords><keyword>rec1</keyword><keyword>druga rec</keyword><keyword>tec treca</keyword><keyword>najnovija rec</keyword><keyword>ja sam rec</keyword></keywords><paragraph/></abstract></scientificPublication>';
    const editor = document.getElementById('ecitor');
    // tslint:disable-next-line: no-unused-expression
    new Xonomy.render(this.publicationXml, editor, docSpec);
  }


  addPublication() {
    this.publicationXml = Xonomy.harvest() as string;
    this.publicationService.addPublication(this.publicationXml)
      .subscribe(res => {
        console.log(res);
      });
  }



}