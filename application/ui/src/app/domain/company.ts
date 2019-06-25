import { InputType, DomainConfig } from '../model/domain-config';

export interface Company {
    cpyId: number|null;
    name: string;
}

export class CompanyDomain implements DomainConfig<Company> {
    rel: 'companies';
    model: Company;
    properties: [
        {
            name: 'cpyId'
            isId: true
        },
        {
            name: 'name',
            options: {
                inputType: InputType.text,
                required: true
            }
        }
    ];
}
