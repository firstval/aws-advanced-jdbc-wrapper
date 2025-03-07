/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package software.amazon.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import software.amazon.jdbc.util.Messages;

public class RandomHostSelector implements HostSelector {

  @Override
  public HostSpec getHost(List<HostSpec> hosts, HostRole role) throws SQLException {
    List<HostSpec> eligibleHosts = hosts.stream()
        .filter(hostSpec -> role.equals(hostSpec.getRole())).collect(Collectors.toList());
    if (eligibleHosts.size() == 0) {
      throw new SQLException(Messages.get("RandomHostSelector.noHostsMatchingRole", new Object[]{role}));
    }

    int randomIndex = new Random().nextInt(eligibleHosts.size());
    return eligibleHosts.get(randomIndex);
  }
}
