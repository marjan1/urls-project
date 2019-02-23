import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
  MatButtonModule,
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatIconModule,
  MatInputModule,
  MatMenuModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatToolbarModule
} from '@angular/material';

@NgModule({
  imports: [CommonModule, MatToolbarModule, MatButtonModule, MatCardModule, MatMenuModule, MatDividerModule,
    MatInputModule, MatDialogModule, MatTableModule, MatIconModule, MatSelectModule, MatSnackBarModule,
  MatPaginatorModule,MatSortModule,
    MatProgressSpinnerModule],
  exports: [CommonModule, MatToolbarModule, MatButtonModule, MatCardModule, MatMenuModule, MatDividerModule,
    MatInputModule, MatDialogModule, MatTableModule, MatIconModule, MatSelectModule, MatSnackBarModule,
    MatPaginatorModule,MatSortModule,
    MatProgressSpinnerModule],
})
export class CustomMaterialModule {
}
