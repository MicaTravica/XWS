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
