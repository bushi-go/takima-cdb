import { ApiCall } from './api';

export interface TabularData {
    header: string[];
    rows: Row[];
}
export interface Row {
    data: {[key: string]: any};
    operation: ApiCall[];
}
