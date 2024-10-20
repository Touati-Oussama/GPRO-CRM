import { MessageService } from './../../services/message.service';
import { ImageService } from './../../services/image.service';
import { SocieteService } from './../../services/societe.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from './../../services/users.service';
import { Component, OnInit, Inject, Optional } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { convertUpdateArguments } from '@angular/compiler/src/compiler_util/expression_converter';
import Swal from 'sweetalert2'
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { HttpEventType, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';
  fileInfos: Observable<any>;
  selectFile(event): void {
    this.selectedFiles = event.target.files;
  }

  
  societies = [];
  id;
  data = new FormGroup(
    {
      id: new FormControl('',[Validators.required]),
      nom: new FormControl('',[Validators.required]),
      username: new FormControl('',[Validators.required]),
      prenom: new FormControl('',[Validators.required]),
      telephone: new FormControl('',[Validators.required]),
      email: new FormControl('',[Validators.required]),
      password: new FormControl('',[Validators.required]),
    
  }
  )
  constructor(private userService:UserService, private route:ActivatedRoute, 
              @Optional() public dialogRef: MatDialogRef<EditComponent>,
              private imageService:ImageService,private messageService:MessageService,
              @Optional() @Inject(MAT_DIALOG_DATA) public customer: any,
              private societeService: SocieteService ,private router:Router)
              {
                if (customer)
                  this.id = customer.customerId;
               }

  ngOnInit(): void {
    if(!this.id)
      this.id = this.route.snapshot.params.id;
    this.userService.getCustomer(this.id).toPromise().then((res:any)=>{
      
      try {
        this.data.setValue(res);
      } catch (error) {
      }
    })

    this.societeService.listeSocieties().toPromise().then((res:any[])=>{
      this.societies = res;
      console.log(res);
    })
 
  }
  update(){
    if(!this.id)
    this.id = this.route.snapshot.params.id;
    this.progress = 0;
    if (this.selectedFiles){
      this.currentFile = this.selectedFiles.item(0);
      this.imageService.update(this.id,this.currentFile).subscribe(res => {
          this.data.addControl('image', new FormControl(res.body.id,[]));
          console.log(this.data.value);
          this.userService.updateCustomers(this.data.value,this.id).subscribe((res:any)=>{
            if (res.type === HttpEventType.UploadProgress) {
              this.progress = Math.round(100 * res.loaded / res.total);
            }
            else if (res instanceof HttpResponse) {
                if(res.body.user_id){
                  this.messageService.send('isAuthenticated');
                  Swal.fire({
                    icon: 'success',
                    title: 'Success...',
                    text: 'Mis à jour avec succés !',
                  })
                  this.OnClose();
                  this.progress = 0;
                }else{
                  Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: "Quelque chose s'est mal passé!",
                  })
                }
              }
            },err => {
                Swal.fire({
                  icon: 'warning',
                  title: "Échec de la mise à jour!...",
                  text: err.error.message,
                })
                }
              )
            
          },
          err => {
          this.progress = 0;
          this.message = 'Could not upload the file!';
          this.currentFile = undefined;
        });
    
    this.selectedFiles = undefined;
  }
  else {
    this.data.addControl('image', new FormControl(null,[]));
    console.log(this.data.value);
    this.userService.updateCustomers(this.data.value,this.id).subscribe((res:any)=>{
      if (res.type === HttpEventType.UploadProgress) {
        this.progress = Math.round(100 * res.loaded / res.total);
      }
      else if (res instanceof HttpResponse) {
          console.log(res.body);
          if(res.body.user_id){
            this.messageService.send('isAuthenticated');
            Swal.fire({
              icon: 'success',
              title: 'Success...',
              text: 'Mis à jour avec succés !',
            })
            this.OnClose();
            this.progress = 0;
          }else{
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: "Quelque chose s'est mal passé!",
            })
          }
        }
      },err => {
          Swal.fire({
            icon: 'warning',
            title: "Échec de la mise à jour!...",
            text: err.error.message,
          })
          }
        )
  }
}

  OnClose(){
    this.dialogRef.close();
    
  }
}

