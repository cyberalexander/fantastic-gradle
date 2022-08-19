/*
 * Copyright 2022 Aliaksandr Leanovich. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leonovich.fantasticgradle.repository;

import com.leonovich.fantasticgradle.model.FantasticGradle;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created : 19/08/2022 12:05
 * Project : fantastic-gradle
 * IDE : IntelliJ IDEA
 *
 * @author Aliaksandr_Leanovich
 * @version 1.0
 */
public interface FantasticGradleCrudRepository extends CrudRepository<FantasticGradle, UUID> {
}
