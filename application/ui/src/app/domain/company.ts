export interface Company{
    cpyId: number|null;
    name: string;
}

export function isCompany(object: any): object is Company {
    return (object as Company).cpyId !== undefined ;
}