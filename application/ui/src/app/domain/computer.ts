import { Company } from './company';
import { DomainConfig, PropertyConfig } from '../model/domain-config';
import {InputType, FieldType} from '../model/field';
import { environment } from 'src/environments/environment';
export class Computer {
    constructor(){
        this.cptId = null;
        this.name= null;
        this.introduced = null
        this.discontinued = null;
        this.company = null;
    }
    cptId: number|null;
    name: string;
    introduced: Date | null;
    discontinued: Date | null;
    company: Company;
}
// Objective : get this from api, in the properties of the template part of the resource
const computerProperties = [
    {
        name: 'cptId',
        isId: true,
    },
    {
        name: 'name',
        options: {
            label: 'Name',
            required: true,
            fieldType: FieldType.input,
            inputType: InputType.text
        },
    },
    {
        name: 'introduced',
        options: {
            label: 'Introduced',
            required: false,
            fieldType: FieldType.datePicker,
            inputType: InputType.date
        }
    },
    {
        name: 'discontinued',
        options: {
            label: 'Discontinued',
            required: false,
            fieldType: FieldType.datePicker,
            inputType: InputType.date
        }
    },
    {
        name: 'company',
        options: {
            label: 'Company',
            required: false,
            fieldType: FieldType.select,
            inputType: InputType.date,
            referenceListUri:  environment.apiUrl + '/companies',
            refRel: 'companies'
        }
    }
]

export class ComputerDomain extends DomainConfig<Computer> {
    constructor(){
        super('computers', () => new Computer(), computerProperties);
    }
    validate = () => {
        // TODO : i do solemnly swear that i will refactor this comparison using moment... '^^
        const areDateCoherent =
             this.model.introduced
                && this.model.discontinued
                && !(this.model.introduced > this.model.discontinued);
        const areRequiredFieldValued = this.properties.filter((prop: PropertyConfig) =>
            prop.options
            && prop.options.required
            && (this.model[prop.name] === undefined || this.model[prop.name] === undefined))
        .length === 0;
        return areDateCoherent && areRequiredFieldValued;
    }
}
