/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateUserDTO } from "../models/CreateUserDTO";
import type { LoggedInSuccessfullyDTO } from "../models/LoggedInSuccessfullyDTO";
import type { LoginDTO } from "../models/LoginDTO";
import type { UserDTO } from "../models/UserDTO";

import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class AuthControllerService {
  /**
   * @param requestBody
   * @returns UserDTO OK
   * @throws ApiError
   */
  public static createUser(
    requestBody: CreateUserDTO,
  ): CancelablePromise<UserDTO> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/auth/users",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns LoggedInSuccessfullyDTO OK
   * @throws ApiError
   */
  public static login(
    requestBody: LoginDTO,
  ): CancelablePromise<LoggedInSuccessfullyDTO> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/auth/login",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param id
   * @returns UserDTO OK
   * @throws ApiError
   */
  public static getUser(id: number): CancelablePromise<UserDTO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/auth/users/{id}",
      path: {
        id: id,
      },
    });
  }
}
