package io.github.frapples.augustrpc.registry;

import io.github.frapples.augustrpc.transport.model.ProviderIdentifier;
import io.github.frapples.augustrpc.transport.model.Request;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.jcip.annotations.ThreadSafe;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 *
 * This is a simple implementation for dev and test now. Current implementations are effective only for providers and consumers within the same process.
 */
@ThreadSafe
public class RegistryManager {

    private final ConcurrentHashMap<String, List<ProviderIdentifier>> providers = new ConcurrentHashMap<>();

    public ProviderIdentifier getProvider(Request request) {
        String fullyQualifiedName = request.getCallId().getServiceFullyQualifiedName();
        do {
            List<ProviderIdentifier> list = this.providers.get(fullyQualifiedName);
            if (list == null || list.isEmpty()) {
                return null;
            }
            int index = (new Random()).nextInt(list.size());
            try {
                return list.get(index);
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
        } while (true);
    }

    public void addProvider(Class<?> clazz, ProviderIdentifier providerIdentifier) {
        String fullyQualifiedName = clazz.getName();
        this.providers.compute(fullyQualifiedName, (key, value) -> {
            if (value == null) {
                value = new CopyOnWriteArrayList<>();
            }
            value.add(providerIdentifier);
            return value;
        });
    }
}
