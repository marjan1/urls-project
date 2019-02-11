export interface LoggedUser {

  sub: string;
  exp: string;
  roles: {authority: string}[];
}

