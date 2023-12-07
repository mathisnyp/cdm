/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MessageRequest } from "../models/MessageRequest";

import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class MessageControllerService {
  /**
   * @param requestBody
   * @returns any OK
   * @throws ApiError
   */
  public static publish(requestBody: MessageRequest): CancelablePromise<any> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/messages",
      body: requestBody,
      mediaType: "application/json",
    });
  }
}
