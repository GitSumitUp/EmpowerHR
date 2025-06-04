 export class Employee {
  id!: number;
  fname!: string;
  lname!: string;
  password!: string;
  email!: string;
  department: Department = new Department();
  doj!: Date|string; 
  active!: number;
  gender!:string;
}
 

 export class Department {
  departmentid!: number;
  dname!: string;
  mgr!: number;
}


