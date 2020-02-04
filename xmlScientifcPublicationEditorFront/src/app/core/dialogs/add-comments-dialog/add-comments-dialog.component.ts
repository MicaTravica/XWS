import { Component, OnInit, Inject } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { DialogData } from 'src/app/revisions/add-revision/add-revision.component';

@Component({
  selector: 'app-add-comments-dialog',
  templateUrl: './add-comments-dialog.component.html',
  styleUrls: ['./add-comments-dialog.component.scss']
})
export class AddCommentsDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AddCommentsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

    ngOnInit() {
    }

  onNoClick(): void {
    this.dialogRef.close(false);
  }
  onYesClick(): void {
    this.dialogRef.close(true);
  }

}
