import { Company} from './company';
import {  Computer } from './computer';

export * from './computer';
export * from './company';

export function isComputer(object: any): object is Computer {
        return (object as Computer).cptId !== undefined;
}
export function isCompany(object: any): object is Company{
        return (object as Company).cpyId !== undefined;
}
