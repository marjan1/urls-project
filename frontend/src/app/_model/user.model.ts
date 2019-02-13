import {Role} from "./role.model";
import {Status} from "./status.model";

export class User {

  id: number;
  name: string;
  surname: string;
  email: string;
  address: string;
  password: string;
  matchingPassword: string;
  phone: string;
  roleDtos: Role[];
  statusDto: Status;
  accountGroupLevel: number;

}
