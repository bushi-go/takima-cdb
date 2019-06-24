import { Affordance, ResourceLinks } from './link';

export interface SingleWebResource {
    [key: string]: any;
    _links: ResourceLinks;
    _templates?: {
        [key: string]: Affordance;
    };
}

export interface WebCollection {
    _embedded: {
        [key: string]: SingleWebResource[];
    };
    _links: ResourceLinks;
    _templates?: {
        [key: string]: Affordance;
    };
}

export type WebResource = WebCollection | SingleWebResource;

export function isWebCollection(resource: any): resource is WebCollection {
    return (resource as WebCollection)._embedded !== undefined;
}

export function isSingleWebResource(resource: any): resource is SingleWebResource {
    return (resource as WebCollection)._embedded === undefined;
}