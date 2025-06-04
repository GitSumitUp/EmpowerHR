import { Component, OnInit} from '@angular/core';
import { NgForm } from '@angular/forms';
import { EmployeeService } from '../employee.service';
import { WebclientService } from '../webclient.service';
import { Department, Employee } from '../entities';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
  standalone: false,
})

export class FormComponent implements OnInit {

  employee:Employee={
    id: 0,
    fname: '',
    lname: '',
    password: '',
    email: '',
    department: new Department,
    doj: "",
    active: 0,
    gender: ''
  }

  imagePaths: string[] = [
  'assets/apple.jpg',
  // 'assets/tesla.jpg',
  // 'assets/facebook.jpg',
  // 'assets/sachin.png',
  // 'assets/rohit.png',
  // 'assets/virat.webp',
  // 'assets/msd.png',
  // 'assets/rahul.webp',
  // 'assets/surya.avif',
  // 'assets/kl.avif'
  ];


  submitted = false;
  showEmployeeList = false; 
  
  employeeList: any[] = [];
  departmentList: any[] = [];

  selectedEmployeeForEdit: any = null;
  isEditing = false;


  constructor(
    private employeeService: EmployeeService,
    private webclient: WebclientService
  ) {}


  // Define a variable to track which image is currently  open
    expandedImageIndex: number | null = null;
    imageUrl: string = '';

   showFullImage(url: string, event: MouseEvent, index: number): void {
   if (this.expandedImageIndex === index) {
    this.expandedImageIndex = null; // Toggle off if already clicked
   } else {
    this.expandedImageIndex = index;
    this.imageUrl = url;
  }
}

  closeImage(): void {
    this.expandedImageIndex = null;
  }


  ngOnInit(): void {
    const today = new Date();
    this.employee.doj = today.toISOString().substring(0, 10);
    this.getEmployees();
    this.employeeService.getDepartments().subscribe((res)=>
    {
      this.departmentList=res;
    })
  }


//post, get, getOne, delete, put

  onSubmit(form: NgForm) {
    // this.submitted = true;
    // this.employee.active = this.employee.active ? 1 : 0; 
    //  if (form.valid) {
    //    this.webclient.post(`/api/employees`, this.employee).subscribe({
    //     next: () => {
    //       console.log(`Employee registered successfully`);
    //       alert(`Employee registered successfully!`);
    //       this.onReset(form);
    //       this.getEmployees();
    //       this.isEditing = false;
    //       this.selectedEmployeeForEdit = null;
    //     },
    //     error: (err: any) => {
    //       console.error(`Error 'saving' employee`, err);
    //         alert(`Error occurred while registering employee.`);
    //     }
    //   });
    // }


  this.submitted = true;
  this.employee.active = this.employee.active ? 1 : 0;
 if (form.valid) {
  // Ensure department is sent as an object
  const payload = {
    ...this.employee,
    department: this.employee.department?.departmentid ? { departmentid: this.employee.department.departmentid } : null
  };

  console.log('Final Employee Payload:', JSON.stringify(payload, null, 2)); // Debugging log

  const apiCall = this.isEditing
    ? this.webclient.put(`/api/employees/${this.selectedEmployeeForEdit.id}`, payload)
    : this.webclient.post(`/api/employees`, payload);

  apiCall.subscribe({
    next: res => {
      console.log(`Employee ${this.isEditing ? 'updated' : 'registered'} successfully`, res);
      alert(`Employee ${this.isEditing ? 'updated' : 'registered'} successfully!`);

      this.onReset(form);
      this.getEmployees();
      this.isEditing = false;
      this.selectedEmployeeForEdit = null;
    },
    error: (err: any) => {
      console.error(`Error ${this.isEditing ? 'updating' : 'registering'} employee`, err);
      alert(`Error occurred while ${this.isEditing ? 'updating' : 'registering'} employee.`);
    }
  });
 }
}



  onEdit(employee: any){
    this.isEditing = true;
    this.selectedEmployeeForEdit = { ...employee};
    // The (...employee) is called the spread operator in JavaScript. It’s used to copy all the properties of an object (employee) into a new object.
    this.employee = { ...employee }; // Populate the form
  }

  

 onDelete(id: number) {
  if (confirm('Are you sure you want to delete this employee?')) {
    this.webclient.delete(`/api/employees/${id}`).subscribe({
      next: () => {
        alert('Employee marked as inactive successfully!');
        this.getEmployees(); 
      },
      error: () => {
        alert('Something went wrong while marking the employee as inactive.');
      }
    });
  }
}


  getEmployees() {
    this.employeeService.getAllEmployees().subscribe({
      next: (data: any) => {
        this.employeeList = data;
        console.log("employeeList: ",this.employeeList);
        
      },
      error: err => {
        console.error('Error fetching employees', err);
      }
    });
  }

  onReset(form: NgForm) {
     form.resetForm();
    this.submitted = false;
    this.isEditing = false;
    this.selectedEmployeeForEdit = null;
     this.employee = {
      id:0,
      fname: '',
      lname: '',
      password: '',
      email: '',
      department: new Department,
      doj: '',
      gender: '',
      active: 0
    };

    const today = new Date();
    this.employee.doj = today.toISOString().substring(0, 10);
  }
}