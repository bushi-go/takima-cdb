import {isCompany, getDomainConfigFor} from '../domain';
import { SingleWebResource, WebCollection } from '../model/web-resource';
import { TabularData, Row } from '../model/tabular-data';
import mapLinkAndAffordancesToApiCalls from './linkUtil';
import { ApiCall } from '../model/api';

function getColumns(object: any) {
    const domain = getDomainConfigFor(object);
    const columns = domain.properties.filter(prop => !prop.isId).map(prop => prop.name);
    return columns;
}

function getDisplayForNestedObjec(object: any ): string {
    if (object != null && isCompany(object)) {
        return object.name;
    }
    return object;
}

function getDataObject(object: SingleWebResource) {
    const keys = Object.keys(object).filter(key => key !== '_links' && key !== '_templates' );
    const dto = {};
    for (const key of keys) {
        dto[key] = getDisplayForNestedObjec(object[key]);
    }
    return dto;
}

function getIconForCall(op: ApiCall): string {
    switch (op.method) {
        case 'post':
            return 'add';
        case 'put':
            return 'import_export';
        case 'patch':
            return 'create';
        case 'delete':
            return 'delete';
    }
}

function getLabelForCall(op: ApiCall): string {
    switch (op.method) {
        case 'post':
            return 'Create';
        case 'put':
            return 'Replace';
        case 'patch':
            return 'Update';
        case 'delete':
            return 'Delete';
    }
}

function getRowForResource(object: SingleWebResource): Row {
    return {
        data: getDataObject(object),
        operations : mapLinkAndAffordancesToApiCalls(object._links, object._templates).map(op => {
            return { call: op, icon: getIconForCall(op), label: getLabelForCall(op)};
        })
    };
}

export function toDataObjectList(collection: WebCollection): any[] {
    const relation = Object.keys(collection._embedded)[0];
    return collection._embedded[relation].map(getDataObject);
}

export function mapWebCollectionToTabularData(collection: WebCollection): TabularData {
    const relation = Object.keys(collection._embedded)[0];
    const sample = getDataObject(collection._embedded[relation][0]);
    return {
        header: getColumns(sample),
        rows: collection._embedded[relation].map(resource => getRowForResource(resource)),
        filter: null
    };
}
