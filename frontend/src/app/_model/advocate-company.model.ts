import {CompanyType} from "./company-type.model";
import {Status} from "./status.model";

export interface AdvocateCompany {

  id: number;
  name: string;
  address: string;
  phone: string;
  email: string;
  embs: string;
  edbs: string;
  license: string;
  digitalSignature: string;
  type: CompanyType;
  status: Status;
}
