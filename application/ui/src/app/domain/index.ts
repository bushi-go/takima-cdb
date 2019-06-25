import { Company, CompanyDomain} from './company';
import {  Computer, ComputerDomain } from './computer';
import { DomainConfig } from '../model/domain-config';

export * from './computer';
export * from './company';

export function isComputer(object: any): object is Computer {
        return (object as Computer).cptId !== undefined;
}
export function isCompany(object: any): object is Company{
        return (object as Company).cpyId !== undefined;
}

export function  getDomainConfigFor(object: any): DomainConfig<any>{
        if (isComputer(object)){
                return new ComputerDomain();
        }
        if (isCompany(object)){
                return new CompanyDomain();
        }
        return;
}
