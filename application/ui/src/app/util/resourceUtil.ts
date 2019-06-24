import {Computer, Company, isCompany, isComputer} from '../domain';
import { SingleWebResource, WebCollection } from '../model/web-resource';
import { TabularData, Row } from '../model/tabularData';
import mapLinkAndAffordancesToApiCalls from './linkUtil';

function getColumns(object: any) {
    if (isCompany(object)) {
        return ['name'];
    }
    if (isComputer(object)) {
        return ['name', 'introduced', 'discontinued', 'company'];
    }
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

function getRowForResource(object: SingleWebResource): Row {
    return {
        data: getDataObject(object),
        operation : mapLinkAndAffordancesToApiCalls(object._links, object._templates)
    };
}

export function mapWebCollectionToTabularData(collection: WebCollection): TabularData {
    const relation = Object.keys(collection._embedded)[0];
    const sample = getDataObject(collection._embedded[relation][0]);
    debugger;
    return {
        header: getColumns(sample),
        rows: collection._embedded[relation].map(resource => getRowForResource(resource))
    };
}
