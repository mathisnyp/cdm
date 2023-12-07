/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class GeoControllerService {
  /**
   * @returns any OK
   * @throws ApiError
   */
  public static sendHello(): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/geo/hello",
    });
  }
}
