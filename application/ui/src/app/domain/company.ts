import {DomainConfig } from '../model/domain-config';
import {InputType, FieldType} from '../model/field';
export class Company {
    constructor() {

    }
    cpyId: number|null;
    name: string;
}
const companyProperties = [
    {
        name: 'cpyId',
        isId: true
    },
    {
        name: 'name',
        options: {
            label: 'Name',
            fieldType: FieldType.input,
            inputType: InputType.text,
            required: true
        }
    }
];
export class CompanyDomain extends DomainConfig<Company> {
    constructor() {
        super('companies', () => new Company(), companyProperties);
    }
}
