import {Role} from "./role.model";
import {Status} from "./status.model";
import {AdvocateCompany} from "./advocate-company.model";

export class CUser {

  id: number;
  name: string;
  surname: string;
  email: string;
  address: string;
  phone: string;
  roles: Role[];
  status: Status;
  advocateCompany: AdvocateCompany;
  accountGroupLevel: number;

}
