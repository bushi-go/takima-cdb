import { Company } from './company';
import { DomainConfig, InputType, PropertyConfig } from '../model/domain-config';

export interface Computer {
    cptId: number|null;
    name: string;
    introduced: Date | null;
    discontinued: Date | null;
    company: Company;
}


export class ComputerDomain implements DomainConfig<Computer> {
    rel: 'computers';
    model: Computer;
    properties: [
        {
            name: 'cptId',
            isId: true,
        },
        {
            name: 'name',
            options: {
                required: true,
                inputType: InputType.text
            },
        },
        {
            name: 'introduced',
            options: {
                required: false,
                inputType: InputType.date
            }
        },
        {
            name: 'discontinued',
            options: {
                required: false,
                inputType: InputType.date
            }
        },
        {
            name: 'company',
            options: {
                required: false,
                inputType: InputType.date,
                autocomplete: false,
                referenceListUri: 'companies'
            }
        }
    ];
    validate() {
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
