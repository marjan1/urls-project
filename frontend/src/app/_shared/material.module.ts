import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
  MatButtonModule,
  MatCardModule,
  MatDialogModule,
  MatIconModule,
  MatInputModule,
  MatSelectModule,
  MatTableModule,
  MatToolbarModule,
} from '@angular/material';

@NgModule({
  imports: [CommonModule, MatToolbarModule, MatButtonModule, MatCardModule,
    MatInputModule, MatDialogModule, MatTableModule, MatIconModule, MatSelectModule],
  exports: [CommonModule, MatToolbarModule, MatButtonModule, MatCardModule,
    MatInputModule, MatDialogModule, MatTableModule, MatIconModule, MatSelectModule],
})
export class CustomMaterialModule { }
