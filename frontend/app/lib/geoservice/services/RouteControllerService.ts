/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CdmPoint } from '../models/CdmPoint';
import type { RouteRequestDTO } from '../models/RouteRequestDTO';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class RouteControllerService {

    /**
     * @param requestBody
     * @returns CdmPoint OK
     * @throws ApiError
     */
    public static getRoute(
        requestBody: RouteRequestDTO,
    ): CancelablePromise<Array<CdmPoint>> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/route',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

}
