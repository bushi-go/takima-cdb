import { Link, Affordance, ResourceLinks } from '../model/link';
import { ApiCall } from '../model/api';

export default function mapLinkAndAffordancesToApiCalls(links: ResourceLinks, affordances?: {[key: string]: Affordance}): ApiCall[] {
    const apiCallList: ApiCall[] = [];
    for (const link of Object.keys(links)) {
        apiCallList.unshift({rel: link, method: 'get', url: links[link].href});
    }

    if (affordances !== undefined && affordances !== null) {
        for (const key of Object.keys(affordances)) {
            apiCallList.unshift({
                rel: 'self',
                method: affordances[key].method,
                url: links.self.href,
                formProperties: affordances[key].properties
            });
        }
    }
    return apiCallList;
}

