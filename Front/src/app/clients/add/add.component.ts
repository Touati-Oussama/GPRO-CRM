import { AuthService } from 'src/app/services/auth.service';
import { ImageService } from 'src/app/services/image.service';
import { HttpClient, HttpEventType, HttpResponse } from '@angular/common/http';
import { SocieteService } from './../../services/societe.service';
import { UserService } from 'src/app/services/users.service';
import { Component, OnInit } from '@angular/core';
import { EmailValidator, FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2'
import { MatDialogRef } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { Role } from 'src/app/model/Role';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {


  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';
  fileInfos: Observable<any>;
  selectFile(event): void {
    this.selectedFiles = event.target.files;
  }
  
  
  roles = [];
  role = Role;  
  societies = [];
  data:FormGroup;
  file:File;
  constructor(private userService:UserService,
    public authService:AuthService,
    public dialogRef: MatDialogRef<AddComponent>,
    private imageService:ImageService,
     private societeService:SocieteService) { }

  ngOnInit(): void {
    this.societeService.listeSocieties().toPromise().then((res:any[])=>{
      this.societies = res;
      console.log(res);
    })
  
    this.roles = Object.keys(this.role);

    this.data = new FormGroup(
      {
        nom: new FormControl('',[Validators.required]),
        username: new FormControl('',[Validators.required]),
        prenom: new FormControl('',[Validators.required]),
        telephone: new FormControl('',[Validators.required,Validators.minLength(8)]),
        email: new FormControl('',[Validators.required,
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
        password: new FormControl('',[Validators.required,Validators.minLength(8)]),
        societe: new FormControl(''),
        role: new FormControl(Role.PERSONNEL_SOCIETE,Validators.required),
      }
    )
  }


    add(){
      /*if(this.authService.isResponsable_Societe)
      {
        this.data.patchValue({'societe': 1});
      
      }*/
    this.userService.getCustomerByUsername(this.authService.loggedUser).subscribe(res=>{
      this.societeService.getSocietyByName(res.societe).subscribe(res=>{
        this.data.patchValue({'societe': res.id})
        console.log(this.data.value);
          this.currentFile = this.selectedFiles.item(0);
          this.imageService.uploadd(this.currentFile).subscribe(res =>{
            if (res){
              console.log(res.body.id);
              this.data.addControl('image', new FormControl(res.body.id,[]));
              console.log(this.data.value);
              this.userService.add(this.data.value).subscribe((res:any)=>{
                if (res.type === HttpEventType.UploadProgress) {
                  this.progress = Math.round(100 * res.loaded / res.total);
                }
                else if (res instanceof HttpResponse){
                  if (res.body.user_id) {
                    this.progress = 0;
                    Swal.fire({
                      icon: 'success',
                      title: 'Success...',
                      text: "l'inscription est réussie !",
                    })
                    this.data.reset();
                    this.currentFile = undefined;
                    try {
                      if(this.dialogRef.getState())
                      this.OnClose();
                    } catch (error) {} 
                  }else{
                    Swal.fire({
                      icon: 'error',
                      title: 'Oops...',
                      text: "Quelque chose s'est mal passé!",
                    })
                  }
                }
              },
              err => {
                Swal.fire({
                  icon: 'warning',
                  title: "Échec de l'inscription!...",
                  text: err.error.message,
                })
                this.currentFile = undefined;
                }
          
              )
            }
          })
      })
    })
    
  

  }

  get f() { return this.data.controls; }

  
  OnClose(){
    this.dialogRef.close();      
  }
}
