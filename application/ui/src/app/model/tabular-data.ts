import { ApiCall } from './api';

export interface TabularData {
    header: string[];
    rows: Row[];
    filter?: string;
    filteredData?: Row[]
}
export interface Row {
    data: {[key: string]: any};
    operations: {call: ApiCall, icon: string}[];
}
