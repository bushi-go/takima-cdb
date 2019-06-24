import { Company } from './company';

export interface Computer {
    cptId: number|null;
    name: string;
    introduced?: Date;
    discontinued?: Date;
    company: Company;
}

