import { Component, OnInit } from '@angular/core';
declare const Xonomy: any;

@Component({
  selector: 'app-add-publication',
  templateUrl: './add-publication.component.html',
  styleUrls: ['./add-publication.component.scss']
})
export class AddPublicationComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    const docSpec = {
      onchange: function () {
        console.log('I been changed now!')
      },
      validate: function (obj) {
        console.log('I be validatin\' now!')
      },
      elements: {
        list: {
          menu: [{
            caption: 'Append an <item>',
            action: Xonomy.newElementChild,
            actionParameter: '<item/>'
          }]
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
    const xml = '<list><item label=\'one\'/><item label=\'two\'/></list>';
    const editor = document.getElementById('ecitor');
    // tslint:disable-next-line: no-unused-expression
    new Xonomy.render(xml, editor, docSpec);
  }

}
